import torch
from torch.utils.data import Sampler

class OnesProportionSampler(Sampler):
    def __init__(self, labels, num_samples=None):
        self.labels = labels
        self.num_samples = num_samples
        self.calculate_probabilities()

    def calculate_probabilities(self):
        ones_counts = (self.labels == 1).sum(dim=1)
        zeros_counts = (self.labels == 0).sum(dim=1)
        total_counts = ones_counts + zeros_counts
        self.probabilities = ones_counts / total_counts
        self.probabilities[self.probabilities == 0] = 0.03

    def __iter__(self):
        if self.num_samples is None:
            self.num_samples = len(self.labels)
        return iter(torch.multinomial(self.probabilities, self.num_samples, replacement=True))

    def __len__(self):
        return self.num_samples